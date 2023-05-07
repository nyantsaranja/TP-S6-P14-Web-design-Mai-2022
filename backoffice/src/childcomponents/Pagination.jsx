export const Pagination = ({event,page, pageNumber, searchQuery}) => {
    return (
        <>
            <nav aria-label="Page navigation example">
                <ul className="pagination">
                    {
                        page > 1 && <li className="page-item"><a className="page-link" onClick={()=>{event(page-1,searchQuery)}}>Previous</a></li>
                    }
                    {
                        pageNumber > 1 ?
                            [...Array(pageNumber).keys()].map((number, index) => {
                                    return (
                                        <li key={index} className="page-item"><a className="page-link"
                                                                                 onClick={()=>{event(number+1,searchQuery)}} style={page===number+1?{backgroundColor:"lightgray"}:{}}>{number + 1}</a></li>
                                    )
                                }
                            ) : <li className="page-item"><a className="page-link" href="#!">{1}</a></li>
                    }
                    {
                        page < pageNumber && <li className="page-item"><a className="page-link" onClick={()=>{event(page+1,searchQuery)}}>Next</a></li>
                    }
                </ul>
            </nav>
        </>
    );
}