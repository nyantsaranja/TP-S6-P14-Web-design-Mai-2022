import {useEffect, useRef, useState} from "react";
import axios from "axios";
import {BASE_URL} from "../service/Api-Call";
import Swal from "sweetalert2";
import {CKEditor} from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import {useParams} from "react-router-dom";

export const ModifyArticle = () => {
    const [categories, setCategories] = useState([]);
    const [ckData, setCkData] = useState('');
    const [article, setArticle] = useState({});
    const categoryRef = useRef(null);
    const summaryRef = useRef(null);
    const titleRef = useRef(null);
    const {id}=useParams();
    useEffect(() => {
        const keyWords = "Add article"
        document.getElementById("mainTitle").innerText = keyWords
        document.getElementById("page-description").innerText = keyWords
        axios.get(`${BASE_URL}/categories`).then((response) => {
                console.log(response.data);
                setCategories(response.data.data);
            }
        ).catch((error) => {
                console.log(error);
            }
        );
        axios.get(`${BASE_URL}/articles/${id}`).then((response) => {
                console.log(response.data);
                setArticle(response.data.data);
                titleRef.current.value = response.data.data.name;
                summaryRef.current.value = response.data.data.summary;
                setCkData(response.data.data.content);
                categoryRef.current.value = response.data.data.category.id;
            }
        ).catch((error) => {
                console.log(error);
            }
        );
    }, []);

    function update() {
        const article = {
            summary: summaryRef.current.value,
            name: titleRef.current.value,
            content: ckData,
            category: {
                id: categoryRef.current.value
            }
        }
        console.log(article);
        axios.put(`${BASE_URL}/articles/${id}`, article).then((response) => {
                console.log(response.data);
                Swal.fire({
                    icon: 'success',
                    title: 'Success',
                    text: 'Data updated successfully'
                })
            }
        ).catch((error) => {
                console.log(error);
            }
        );
    }

    return (
        <>
            <div className="card">
                <div className="card-body">

                    <form>
                        <div className="form-group">
                            <label htmlFor="title" className="form-label">Title</label>
                            <input ref={titleRef} type="text" className="form-control" id="title"
                                   placeholder="Title"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="category" className="form-label">Category</label>
                            <select ref={categoryRef} id="category" className="form-select mb-3">
                                {
                                    categories.map((category) => {
                                            return <option key={category.id} value={category.id}>{category.name}</option>
                                        }
                                    )
                                }
                            </select>
                        </div>
                        <div className="form-group">
                            <label htmlFor="exampleInputPassword1" className="form-label">Summary</label>
                            <textarea ref={summaryRef} className="form-control" rows="5"
                                      placeholder="Type something..."></textarea>
                        </div>
                        <div className="form-group">
                            <CKEditor
                                editor={ClassicEditor}
                                data="Write the content here!"
                                onReady={editor => {
                                    // setCkData(article.content)
                                }}
                                onChange={(event, editor) => {
                                    const data = editor.getData();
                                    console.log({event, editor, data});
                                    setCkData(data)
                                }}
                            />
                        </div>

                        <button onClick={update} type="button" className="btn btn-primary">Update</button>
                    </form>
                </div>
            </div>
        </>
    );
}