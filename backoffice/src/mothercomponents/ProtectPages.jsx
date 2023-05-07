export const ProtectPages = () => {
    if(sessionStorage.getItem("author") === null || localStorage.getItem("token") === null){
        window.location.href = "/"
    }

    return (
        <></>
    );
}