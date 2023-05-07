import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import axios from "axios";
import {BASE_URL} from "../service/Api-Call";

export const Article = () => {
    const {params} = useParams();
    const paramsArray = params.split("-")
    const id = paramsArray[paramsArray.length - 1]
    const [article, setArticle] = useState({});
    useEffect(() => {
        console.log(id)
        axios.get(`${BASE_URL}/articles/${id}`).then((response) => {
                console.log(response.data);
                setArticle(response.data.data);
                document.getElementById("metadescription").content = response.data.data.summary
                const keyWords = response.data.data.name
                document.getElementById("mainTitle").innerText = keyWords
                document.getElementById("page-description").innerText = keyWords
            }
        ).catch((error) => {
                console.log(error);
            }
        );
    }, []);

    return (
        <div className="card">
            <div className="card-body">
                <h2 className="card-title">{article?.name}</h2>
                <hr/>
                <h3>{article?.category?.name}</h3>
                <br/>
                <p className="card-text" dangerouslySetInnerHTML={{__html:article?.content}}/>
            </div>
        </div>
    );
}