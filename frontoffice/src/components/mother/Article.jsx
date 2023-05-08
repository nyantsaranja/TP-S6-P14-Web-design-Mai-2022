import {PostHeading} from "../child/PostHeading";
import {ArticleDetail} from "../child/ArticleDetail";
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";

export const Article = () => {
    const {params} = useParams();
    const paramsArray = params.split("-")
    const id = paramsArray[paramsArray.length - 1]
    const [article, setArticle] = useState({});
    let articleTitle = paramsArray.slice(0, paramsArray.length - 1).reduce((acc, curr) => {
        return acc + " " + curr;
    });
    document.getElementById("pageTitle").innerText = "Blog AI - " + articleTitle
    document.getElementById("pageDescription").innerText = "Blog AI - " + articleTitle + " article";
    useEffect(() => {
        let articles = JSON.parse(sessionStorage.getItem("articles"));
        if (articles) {
            let article = articles.find(article => article.id == id);
            if (article) {
                console.log(article);
                setArticle(article);
            }
        }
    }, []);
    return (
        <>
            <PostHeading article={article}/>
            <ArticleDetail content={article.content}/>
        </>
    );
}