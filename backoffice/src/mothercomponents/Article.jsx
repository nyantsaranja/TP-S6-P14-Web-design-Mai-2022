import {useEffect, useRef, useState} from "react";
import {useParams} from "react-router-dom";
import axios from "axios";
import {BASE_URL, CONFIG} from "../service/Api-Call";
import {getAuthorId} from "../service/Utility";
import Swal from "sweetalert2";
import {Header} from "./Header";
import {CKEditor} from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";

export const Article = () => {
    const {params} = useParams();
    const paramsArray = params.split("-")
    const id = paramsArray[paramsArray.length - 1]
    const [ckData, setCkData] = useState('');
    const summaryRef = useRef(null);
    const subtitleRef = useRef(null);
    const titleRef = useRef(null);
    const [selectedFile, setSelectedFile] = useState(null);
    const [previewImage, setPreviewImage] = useState(null);
    document.getElementById("metadescription").content = "Add an Article"
    useEffect(() => {
        axios.get(`${BASE_URL}/article/${id}`, CONFIG)
            .then(response => {
                    console.log(response.data);
                    let article = response.data.data;
                    setCkData(article.content);
                    subtitleRef.current.value = article.subtitle;
                    titleRef.current.value = article.title;
                    summaryRef.current.value = article.summary;
                    setPreviewImage(article.image);
                    document.getElementById("mainTitle").innerHTML = article.title;
                }
            ).catch((error) => {
                console.log(error);
            }
        );
    }, []);

    const fileSelectedHandler = event => {
        const file = event.target.files[0];
        if (file && validateFile(file)) {
            setSelectedFile(file);
            // create preview image
            const reader = new FileReader();
            // resizes the image 200px width while maintaining aspect ratio
            reader.onloadend = () => {
                setPreviewImage(reader.result);
            }
            reader.readAsDataURL(file);
        } else {
            setSelectedFile(null);
            setPreviewImage(null);
        }
    };

    const validateFile = file => {
        // check file type
        if (!file.type.includes('image/')) {
            alert('Please select an image file.');
            return false;
        }
        // check file size
        if (file.size / 1024 / 1024 > 5) {
            alert('File size must be less than 5 MB.');
            return false;
        }
        return true;
    };

    const fileUploadHandler = async () => {
        document.getElementById("saveButton").disabled = true;
        if(selectedFile===null|| selectedFile===undefined){
            updateArticle({data:{link:previewImage}})
            return;
        }
        if(titleRef.current.value==="" || titleRef.current.value===undefined){
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Title is required!',
            })
            return;
        }
        // create canvas element
        const canvas = document.createElement('canvas');
        const ctx = canvas.getContext('2d');
        let headers = new Headers();
        headers.append("Authorization", "Client-ID 0ec9d7b36980fb5")
        let formData = new FormData();
        // create image element
        const img = new Image();
        let bs64 = null;
        img.onload = () => {
            // calculate new dimensions while maintaining aspect ratio
            // calculate new dimensions while maintaining aspect ratio
            const width = img.width;
            const height = img.height;


            // create a canvas element to draw the resized image
            const canvas = document.createElement('canvas');
            canvas.width = width;
            canvas.height = height;

            // draw the resized image onto the canvas
            const ctx = canvas.getContext('2d');
            ctx.drawImage(img, 0, 0, width, height, 0, 0, width, height);

            // get the Base64-encoded string of the canvas image with maximum quality
            let bs64 = canvas.toDataURL('image/jpeg', 0.5);
            // get image size and reduce it if more than 500kb
            let size = bs64.length * 3 / 4 / 1024 / 1024;
            if (size > 0.5) {
                let quality = 0.5 / size;
                bs64 = canvas.toDataURL('image/jpeg', quality);
            }
            let removePrefix = bs64.split(",")[1];
            formData.append("image", removePrefix);


            fetch("https://api.imgur.com/3/image", {
                method: "post",
                headers: headers,
                body: formData,
                redirect: "follow"
            }).then((response) => {
                return response.json();
            }).then((data) => {
                updateArticle(data)
            }).catch((error) => {
                console.log(error);
            })
        };
        // set image source to preview image
        img.src = previewImage;
    };

    const updateArticle = (data) => {
        console.log(data);
        const article = {
            summary: summaryRef.current.value,
            title: titleRef.current.value,
            subtitle: subtitleRef.current.value,
            content: ckData,
            image: data.data.link,
            author: {
                id: getAuthorId()
            },
            publicationDate: new Date()
        }
        axios.put(`${BASE_URL}/article/${paramsArray[paramsArray.length - 1]}`, article, CONFIG).then((response) => {
                console.log(response.data);
                Swal.fire({
                    icon: 'success',
                    title: 'Success',
                    text: 'Data updated successfully'
                }).then(() => {
                        // reload page
                        document.getElementById("saveButton").disabled = false;
                        window.location.reload();
                    }
                );
            }
        ).catch((error) => {
                document.getElementById("saveButton").disabled = false;
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Something went wrong'
                });
            }
        );
    }

    return (
        <>
            <Header pagename={titleRef.current.value}/>
            <div className="card">
                <div className="card-body">

                    <form>
                        <div className="form-group">
                            <label htmlFor="title" className="form-label">Title</label>
                            <input ref={titleRef} type="text" className="form-control" id="title"
                                   placeholder="Title"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="exampleInputPassword1" className="form-label">Subtitle</label>
                            <input ref={subtitleRef} type="text" className="form-control" id="subtitle"
                                   placeholder="Subtitle"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="exampleInputPassword1" className="form-label">Summary</label>
                            <textarea ref={summaryRef} className="form-control" rows="5"
                                      placeholder="Type something..."></textarea>
                        </div>
                        <div className="form-group">
                            <label htmlFor="exampleInputPassword1" className="form-label">Content</label>
                            <CKEditor
                                editor={ClassicEditor}
                                data={ckData}
                                onReady={editor => {

                                }}
                                onChange={(event, editor) => {
                                    const data = editor.getData();
                                    console.log({event, editor, data});
                                    setCkData(data)
                                }}
                            />
                        </div>
                        {/*add input file for uploading and image*/}
                        <div className="form-group">
                            <label htmlFor="exampleInputPassword1" className="form-label">Illustration</label>
                            <input onChange={fileSelectedHandler} type="file" className="form-control" id="image"/>
                            <div style={{
                                width: "100%",
                                display: "flex",
                                alignItems: "center",
                                justifyContent: "center",
                                marginTop: "10px"
                            }}>
                                {previewImage &&
                                    <img src={previewImage} alt="Preview" style={{
                                        maxWidth: '300px',
                                        maxHeight: '300px',
                                        width: 'auto',
                                        height: 'auto'
                                    }}/>}

                            </div>
                        </div>

                        <button onClick={fileUploadHandler} id="saveButton" type="button"
                                className="btn btn-primary">Save
                        </button>
                    </form>
                </div>
            </div>
        </>
    );
}