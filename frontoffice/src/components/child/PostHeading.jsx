export const PostHeading = () => {
    return (
        <>
            <header className="masthead" style={{backgroundImage: "url('/assets/img/post-bg.jpg')"}}>
                <div className="container position-relative px-4 px-lg-5">
                    <div className="row gx-4 gx-lg-5 justify-content-center">
                        <div className="col-md-10 col-lg-8 col-xl-7">
                            <div className="post-heading">
                                <h1>Man must explore, and this is exploration at its greatest</h1>
                                <h2 className="subheading">Problems look mighty small from 150 miles up</h2>
                                <span className="meta">
                                Posted by
                                <a href="#!">Start Bootstrap</a>
                                on August 24, 2023
                            </span>
                            </div>
                        </div>
                    </div>
                </div>
            </header>
        </>
    );
}