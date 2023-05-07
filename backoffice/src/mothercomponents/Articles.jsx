import {useEffect, useState} from "react";
import axios from "axios";
import {BASE_URL, CONFIG} from "../service/Api-Call";
import {formatDate2} from "../service/Utility";
import {Header} from "./Header";
import Swal from "sweetalert2";

export const Articles = () => {
    const [articles, setArticles] = useState([]);
    document.getElementById("metadescription").content = "AI blog articles"
    useEffect(() => {
            axios.get(`${BASE_URL}/article`).then((response) => {
                    console.log(response.data);
                    setArticles(response.data.data.elements);
                }
            ).catch((error) => {
                    console.log(error);
                }
            );
        }
        , []);

    function deleteArticle(id) {
        // add swall confirmation
        Swal.fire({
                title: 'Are you sure?',
                text: 'You will not be able to recover this article!',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Yes, delete it!',
                cancelButtonText: 'No, keep it',
            }
        ).then((result) => {
                if (result.isConfirmed) {
                    axios.delete(`${BASE_URL}/article/${id}`, CONFIG).then((response) => {
                            console.log(response.data);
                            setArticles(articles.filter(article => article.id !== id))
                        }
                    ).catch((error) => {
                            console.log(error);
                        }
                    );
                } else if (result.dismiss === Swal.DismissReason.cancel) {
                    Swal.fire(
                        'Cancelled',
                        'Your article is safe :)',
                        'error'
                    )
                }
            }
        )
    }


    return (
        <>
            <div style={{display: "flex", justifyContent: "space-between", alignItems: "center"}}>
                <Header pagename="AI blog articles"/>
                <div className="col-auto">

                    <a href="/ai-blog/add-article" className="btn btn-primary lift">
                        New article
                    </a>

                </div>
            </div>

            <div className="card">
                <div className="card-body">
                    <div className="table-responsive">
                        <table className="table table-sm">
                            <thead>
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Title</th>
                                <th scope="col">Subtitle</th>
                                <th scope="col">Publication date</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                articles.map((article, index) => {
                                        return (
                                            <tr key={index}>
                                                <th>{article.id}</th>
                                                <td><a href={"/ai-blog/article/" + article.slug}>{article.title}</a></td>
                                                <td>{article.subtitle}</td>
                                                <td>{formatDate2(article.publicationDate)}</td>
                                                <td>
                                                    <button className="btn btn-danger"
                                                            onClick={() => {
                                                                deleteArticle(article.id)
                                                            }}>Delete
                                                    </button>
                                                </td>
                                            </tr>
                                        )
                                    }
                                )
                            }
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </>
    );
}