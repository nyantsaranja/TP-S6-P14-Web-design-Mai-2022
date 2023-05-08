import {useEffect, useState} from "react";
import axios from "axios";
import {BASE_URL, CONFIG} from "../service/Api-Call";
import {formatDate2} from "../service/Utility";
import {Header} from "./Header";
import Swal from "sweetalert2";
import {Pagination} from "../childcomponents/Pagination";
import {Search} from "../childcomponents/Search";

export const Articles = () => {
    const [articles, setArticles] = useState([]);
    const [page, setPage] = useState(1);
    const [pageNumber, setPageNumber] = useState(1);
    const [searchQuery, setSearchQuery] = useState("");
    document.getElementById("metadescription").content = "AI blog articles"
    useEffect(() => {
            getArticles(2, searchQuery);
        }
        , []);

    function getArticles(page, sQ) {
        axios.get(`${BASE_URL}/article?page=${page}` + sQ+"&order=id,1").then((response) => {
                console.log(response.data);
                setPageNumber(Math.ceil(response.data.data.count / response.data.data.pageSize));
                setPage(response.data.data.page)
                setSearchQuery(sQ)
                setArticles(response.data.data.elements);
            }
        ).catch((error) => {
                console.log(error);
            }
        );
    }

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
                    <Search search={getArticles} page={page}/>
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
                    <div style={{width: "100%", display: "flex", alignItems: "center", justifyContent: "center"}}>
                        <Pagination event={getArticles} page={page} pageNumber={pageNumber} searchQuery={searchQuery}/>
                    </div>
                </div>
            </div>
        </>
    );
}