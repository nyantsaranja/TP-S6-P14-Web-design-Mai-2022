import {ArticleLi} from "../child/ArticleLi";
import {IndexHeading} from "../child/IndexHeading";

export const Articles = () => {
    return (
        <>
            <IndexHeading/>
            <div className="container px-4 px-lg-5">
                <div className="row gx-4 gx-lg-5 justify-content-center">
                    <div className="col-md-10 col-lg-8 col-xl-7">
                        <ArticleLi/>
                        <ArticleLi/>
                        <ArticleLi/>
                        <div className="d-flex justify-content-end mb-4"><a className="btn btn-primary text-uppercase"
                                                                            href="src/components#!">Older Posts →</a></div>
                    </div>
                </div>
            </div>
        </>
    );
}