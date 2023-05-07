export const Logout = () => {
    sessionStorage.removeItem("author");
    localStorage.removeItem("token");
    window.location.href = "/"
    return (
        <></>
    );
}