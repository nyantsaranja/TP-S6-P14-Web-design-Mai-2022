import {CKEditor} from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import {useRef, useState} from "react";
import axios from "axios";
import {BASE_URL, CONFIG} from "../service/Api-Call";
import Swal from "sweetalert2";
import {getAuthorId} from "../service/Utility";
import {Header} from "./Header";

export const AddArticle = () => {
    const [ckData, setCkData] = useState('');
    const summaryRef = useRef(null);
    const subtitleRef = useRef(null);
    const titleRef = useRef(null);
    const [selectedFile, setSelectedFile] = useState(null);
    const base64Ref = useRef(null);
    const [previewImage, setPreviewImage] = useState(null);
    document.getElementById("metadescription").content = "Add an Article"

    const fileSelectedHandler = event => {
        const file = event.target.files[0];
        if (file && validateFile(file)) {
            setSelectedFile({
                file: file
            });
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
        // if no file is selected, return
        if (selectedFile===null || selectedFile===undefined) {
            // Swal alert
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Please select an image file.',
            })
            return;
        }
        if(titleRef.current.value==="" || titleRef.current.value===undefined){
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Please enter a title.',
            })
            return;
        }
        document.getElementById("saveButton").disabled = true;
        let headers = new Headers();
        headers.append("Authorization", "Client-ID 0ec9d7b36980fb5")
        let formData = new FormData();
        // create canvas element
        const canvas = document.createElement('canvas');
        const ctx = canvas.getContext('2d');

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
                axios.post(`${BASE_URL}/article`, article, CONFIG).then((response) => {
                        console.log(response.data);
                        Swal.fire({
                            icon: 'success',
                            title: 'Success',
                            text: 'Data saved successfully'
                        })
                        summaryRef.current.value = '';
                        titleRef.current.value = '';
                        setCkData('');
                        subtitleRef.current.value = '';
                        setPreviewImage(null);
                        document.getElementById("image").value = null;
                        document.getElementById("saveButton").disabled = false;
                    }
                ).catch((error) => {
                        document.getElementById("saveButton").disabled = false;
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: error.response.data.message
                        })
                    }
                );

            }).catch((error) => {
                console.log(error);
            })
        }
        img.src = previewImage;

    };

    // set image source to preview image

    return (
        <>
            <Header pagename={"Add an article"}/>
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