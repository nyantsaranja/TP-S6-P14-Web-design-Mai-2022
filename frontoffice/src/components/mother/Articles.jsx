import {ArticleLi} from "../child/ArticleLi";
import {IndexHeading} from "../child/IndexHeading";
import {useEffect, useState} from "react";
import {BASE_URL} from "../../service/Api-Call";
import axios from "axios";
import {SearchForm} from "../child/SearchForm";

export const Articles = () => {
    const [articles, setArticles] = useState([]);
    const [page, setPage] = useState(1);
    const [pageNumber, setPageNumber] = useState(1);
    useEffect(() => {
            getArticles(page)
        }
        , []);

    function getArticles(page) {
        axios.get(`${BASE_URL}/article?page=${page}`).then((response) => {
                console.log(response.data);
                setPageNumber(Math.ceil(response.data.data.count / response.data.data.pageSize));
                setPage(response.data.data.page)
                setArticles(response.data.data.elements);
            }
        ).catch((error) => {
                console.log(error);
            }
        );
    }

    return (
        <>
            <IndexHeading/>
            <div className="container px-4 px-lg-5">
                <div className="row gx-4 gx-lg-5 justify-content-center">
                    <div className="col-md-10 col-lg-8 col-xl-7">
                        <SearchForm/>
                        {
                            articles.map((article, index) => {
                                    return (
                                        <ArticleLi key={index} id={article.id} title={article.title}
                                                   subtitle={article.subtitle} content={article.content}
                                                   publicationDate={article.publicationDate} image={article.image}
                                                   author={article.author}/>
                                    )
                                }
                            )
                        }
                        <div className="pagination"
                             style={{display: "flex", alignItems: "center", justifyContent: "space-between"}}>
                            {
                                page > 1 &&
                                <button className="btn btn-primary text-uppercase" onClick={() => {
                                    getArticles(page - 1)
                                }}>← Newer Posts</button>
                            }
                            {/*buttons with number based on pagenumber*/}
                            {
                                [...Array(pageNumber).keys()].map((number, index) => {
                                        return (
                                            <button key={index} className="btn btn-primary text-uppercase"
                                                    onClick={() => {
                                                        getArticles(number + 1)
                                                    }}>{number + 1}</button>
                                        )
                                    }
                                )
                            }
                            {
                                page < pageNumber &&
                                <button className="btn btn-primary text-uppercase" onClick={() => {
                                    getArticles(page + 1)
                                }}>Older Posts →</button>
                            }
                        </div>
                        <br/>
                    </div>
                </div>
            </div>
        </>
    );
}