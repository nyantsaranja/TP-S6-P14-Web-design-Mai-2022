export const Error = () => {
    return (
        <>
            <div className="container">
                <div className="row justify-content-center">
                    <div className="col-12 col-md-5 col-xl-4 my-5">

                        <div className="text-center">

                            <h6 className="text-uppercase text-muted mb-4">
                                404 error
                            </h6>

                            <h1 className="display-4 mb-3">
                                There’s no page here 😭
                            </h1>

                            <p className="text-muted mb-4">
                                Looks like you ended up here by accident?
                            </p>

                            <a href="/" className="btn btn-lg btn-primary">
                                Login
                            </a>

                        </div>

                    </div>
                </div>
            </div>
        </>
    );
}