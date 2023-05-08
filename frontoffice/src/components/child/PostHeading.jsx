export const PostHeading = ({article}) => {
    return (
        <>
            <header className="masthead" style={{
                backgroundImage: `url(${article.image})`,
                backgroundPosition: "center",
                backgroundRepeat: "no-repeat",
                backgroundSize: "contain",
                backgroundColor: "black"
            }}>
                <div className="container position-relative px-4 px-lg-5">
                    <div className="row gx-4 gx-lg-5 justify-content-center">
                        <div className="col-md-10 col-lg-8 col-xl-7">
                            <div className="post-heading">
                                <h1>{article.title === undefined ? "Article title" : article.title}</h1>
                                <h2 className="subheading">{article.subtitle === undefined ? "Article subtitle" : article.subtitle}</h2>
                                <span className="meta">
                                Posted by
                    <b>{" " + ((article?.author?.lastname === undefined) ? "Lastname" : article?.author?.lastname) + " " + ((article?.author?.firstname === undefined) ? "Firstname":(article?.author?.firstname))+ " "}</b>
                    on {article.publicationDate===undefined ? new Date().toLocaleDateString("en-US"): new Date(article.publicationDate).toLocaleDateString("en-US")}
                            </span>
                            </div>
                        </div>
                    </div>
                </div>
            </header>
        </>
    );
}