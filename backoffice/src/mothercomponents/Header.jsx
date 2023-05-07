export const Header = ({pagename}) => {
    return (
        <>
            <div className="header myheader">
                <div className="container-fluid">

                    <div className="header-body">
                        <div className="row align-items-end">
                            <div className="col">

                                <h6 className="header-pretitle">
                                    Page
                                </h6>

                                <h1 className="header-title" id="page-description">
                                    {pagename}
                                </h1>

                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </>
    );
}