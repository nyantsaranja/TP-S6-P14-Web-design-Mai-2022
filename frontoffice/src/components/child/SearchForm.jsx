import {useEffect, useRef, useState} from "react";
import axios from "axios";
import {BASE_URL} from "../../service/Api-Call";

export const SearchForm = ({search,page}) => {
    // const [authors, setAuthors] = useState([]);
    const titleRef = useRef(null);
    const subtitleRef = useRef(null);
    const authorRef = useRef(null);
    const minDateRef = useRef(null);
    const maxDateRef = useRef(null);
    // useEffect(() => {
    //     axios.get(`${BASE_URL}/author`).then((response) => {
    //             console.log(response.data);
    //             setAuthors(response.data.data.elements);
    //         }
    //     ).catch((error) => {
    //             console.log(error);
    //         }
    //     );
    //
    // }, []);

    function s() {
        let query= "";
        if(titleRef.current.value !== ""){
            query += "&ilike_title="+titleRef.current.value;
        }
        if(subtitleRef.current.value !== ""){
            query += "&ilike_subtitle="+subtitleRef.current.value;
        }
        if(authorRef.current.value !== ""){
            query += "&author.id="+authorRef.current.value;
        }
        if(minDateRef.current.value !== ""){
            query += "&min_publicationDate="+minDateRef.current.value;
        }
        if(maxDateRef.current.value !== ""){
            query += "&max_publicationDate="+maxDateRef.current.value;
        }
        search(page,query);
    }

    return (
        <>
            <div className="container">
                <div className="row">
                    <div className="col-md-6 mb-3">
                        <label htmlFor="titleInput" className="form-label">Title</label>
                        <input ref={titleRef} type="text" className="form-control" id="titleInput" placeholder="Enter title"/>
                    </div>
                    <div className="col-md-6 mb-3">
                        <label htmlFor="subtitleInput" className="form-label">Subtitle</label>
                        <input ref={subtitleRef} type="text" className="form-control" id="subtitleInput" placeholder="Enter subtitle"/>
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-6 mb-3">
                        <label htmlFor="authorSelect" className="form-label">Author</label>
                        <select ref={authorRef} className="form-select" id="authorSelect">
                            <option defaultValue={true} value={""}>Choose...</option>
                            <option value={1}>Ny Antsa Ranja</option>
                            {/*{*/}
                            {/*    authors.map((author, index) => {*/}
                            {/*        return (*/}
                            {/*            <option key={index} value={author.id}>{author.lastname+" "+author.firstname}</option>*/}
                            {/*        )*/}
                            {/*    })*/}
                            {/*}*/}
                        </select>
                    </div>
                    <div className="col-md-3 mb-3">
                        <label htmlFor="minDateInput" className="form-label">Min Pub. Date</label>
                        <input ref={minDateRef} type="date" className="form-control" id="minDateInput"/>
                    </div>
                    <div className="col-md-3 mb-3">
                        <label htmlFor="maxDateInput" className="form-label">Max Pub. Date</label>
                        <input ref={maxDateRef} type="date" className="form-control" id="maxDateInput"/>
                    </div>
                </div>
                {/*    button search*/}
                <div className="row">
                    <div className="col-md-12 mb-3">
                        <button type="button" className="btn btn-primary" onClick={s}>Search</button>
                    </div>
                </div>
            </div>
            <br/>
            <hr/>
            <br/>

        </>
    );
}