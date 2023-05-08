export const ArticleLi = ({slug,title,subtitle,publicationDate,author}) => {
    return (
        <>
            <div className="post-preview">
                <a href={"/blog-ai/article/"+slug}>
                    <h2 className="post-title">{title}</h2>
                    <h3 className="post-subtitle">{subtitle==="" ? "--": subtitle}</h3>
                </a>
                <p className="post-meta">
                    Posted by
                    <i style={{color:"maroon"}}>{" "+author.lastname+" "+author.firstname+" "}</i>
                    on {new Date(publicationDate).toLocaleDateString("en-US")}
                </p>
            </div>
            <hr className="my-4"/>
        </>
    );
}