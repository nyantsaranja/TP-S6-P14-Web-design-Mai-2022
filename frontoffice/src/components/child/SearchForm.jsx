import {useEffect, useRef, useState} from "react";
import axios from "axios";
import {BASE_URL} from "../../service/Api-Call";

export const SearchForm = () => {
    const [authors, setAuthors] = useState([]);
    const titleRef = useRef(null);
    const subtitleRef = useRef(null);
    const authorRef = useRef(null);
    const minDateRef = useRef(null);
    const maxDateRef = useRef(null);
    useEffect(() => {
        axios.get(`${BASE_URL}/author`).then((response) => {
                console.log(response.data);
                setAuthors(response.data.data.elements);
            }
        ).catch((error) => {
                console.log(error);
            }
        );

    }, []);

    function search() {
        
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
                            <option selected>Choose...</option>
                            {
                                authors.map((author, index) => {
                                    return (
                                        <option key={index} value={author.id}>{author.lastname+" "+author.firstname}</option>
                                    )
                                })
                            }
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
                        <button type="button" className="btn btn-primary" onClick={search}>Search</button>
                    </div>
                </div>
            </div>
            <br/>
            <hr/>
            <br/>

        </>
    );
}