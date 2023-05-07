import {PostHeading} from "../child/PostHeading";
import {ArticleDetail} from "../child/ArticleDetail";
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import {BASE_URL} from "../../service/Api-Call";

export const Article = () => {
    const {params} = useParams();
    const paramsArray = params.split("-")
    const id = paramsArray[paramsArray.length - 1]
    const [article, setArticle] = useState({});
    let articleTitle =  paramsArray.slice(0, paramsArray.length - 1).reduce((acc, curr) => {
        return acc + " " + curr;
    });
    document.getElementById("pageTitle").innerText ="Blog AI - "+ articleTitle
    document.getElementById("pageDescription").innerText = "Blog AI - "+articleTitle+" article";
    useEffect(() => {
        axios.get(`${BASE_URL}/article/${id}`).then((response) => {
                console.log(response.data.data);
                setArticle(response.data.data);
            }
        ).catch((error) => {
                console.log(error);
            }
        );
    }, []);
    return (
        <>
            <PostHeading article={article}/>
            <ArticleDetail content={article.content}/>
        </>
    );
}