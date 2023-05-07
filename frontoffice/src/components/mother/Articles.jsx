import {ArticleLi} from "../child/ArticleLi";
import {IndexHeading} from "../child/IndexHeading";
import {useEffect, useState} from "react";
import {BASE_URL} from "../../service/Api-Call";
import axios from "axios";

export const Articles = () => {
    const [articles, setArticles] = useState([]);
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

    return (
        <>
            <IndexHeading/>
            <div className="container px-4 px-lg-5">
                <div className="row gx-4 gx-lg-5 justify-content-center">
                    <div className="col-md-10 col-lg-8 col-xl-7">
                        {
                            articles.map((article, index) => {
                                    return (
                                        <ArticleLi key={index} id={article.id} title={article.title} subtitle={article.subtitle} content={article.content} publicationDate={article.publicationDate} image={article.image} author={article.author}/>
                                    )
                                }
                            )
                        }
                        <div className="d-flex justify-content-end mb-4"><a className="btn btn-primary text-uppercase"
                                                                            href="src/components#!">Older Posts →</a>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}