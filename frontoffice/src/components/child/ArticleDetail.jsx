export const ArticleDetail = ({content}) => {

    return (
        <>
            <article className="mb-4" style={{minHeight:"400px"}}>
                <div className="container px-4 px-lg-5">
                    <div className="row gx-4 gx-lg-5 justify-content-center">
                        <div className="col-md-10 col-lg-8 col-xl-7" dangerouslySetInnerHTML={{__html:content}}>
                        </div>
                    </div>
                </div>
            </article>
        </>
    );
}