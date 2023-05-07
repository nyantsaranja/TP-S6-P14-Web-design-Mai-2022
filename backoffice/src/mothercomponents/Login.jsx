import {useEffect, useRef} from "react";
import Swal from "sweetalert2";
import axios from "axios";
import {BASE_URL} from "../service/Api-Call";

export const Login = () => {
    const emailRef = useRef(null);
    const passwordRef = useRef(null);


    useEffect(() => {
        if (localStorage.getItem("token") !== null || sessionStorage.getItem("author") !== null) {
            sessionStorage.removeItem("author");
            localStorage.removeItem("token");
            // reload page
            window.location.reload();
        }
    }, []);


    function login() {
        let email = emailRef.current.value;
        let password = passwordRef.current.value;
        if (email === "" || password === "") {
            Swal.fire({
                title: 'Error!',
                text: 'Please fill in all fields!',
                icon: 'error',
                confirmButtonText: 'Ok'
            })
            return
        }
        const obj = {
            email: email,
            password: password
        }
        axios.post(`${BASE_URL}/author/login`, obj).then((response) => {
                let authorId = response.data.data.entity.id;
                console.log(authorId)
                sessionStorage.setItem("author", authorId)
                localStorage.setItem("token", response.data.data.token)
                window.location.href = "/ai-blog/articles"
            }
        ).catch((error) => {
                Swal.fire({
                    icon: 'error',
                    title: 'Désolé...',
                    text: error.response.data.code + ': ' + error.response.data.message,
                })
            }
        )
    }

    return (
        <>
            <div className="container">
                <div className="row justify-content-center">
                    <div className="col-12 col-md-5 col-xl-4 my-5">

                        <h1 className="display-4 text-center mb-3">
                            Sign in
                        </h1>

                        <p className="text-muted text-center mb-5">
                            Blog AI admin dashboard.
                        </p>

                        <form>

                            <div className="form-group">

                                <label className="form-label">
                                    Email Address
                                </label>

                                <input ref={emailRef} type="email" className="form-control"
                                       placeholder="name@address.com"
                                />

                            </div>

                            <div className="form-group">
                                <div className="row">
                                    <div className="col">

                                        <label className="form-label">
                                            Password
                                        </label>

                                    </div>
                                </div>

                                <div className="input-group input-group-merge">

                                    <input ref={passwordRef} className="form-control" type="password"
                                           placeholder="Enter your password"
                                    />

                                    <span className="input-group-text">
                </span>

                                </div>
                            </div>
                            <button type="button" onClick={login} className="btn btn-lg w-100 btn-primary mb-3"
                            >
                                Sign in
                            </button>

                        </form>

                    </div>
                </div>
            </div>
        </>
    );
}