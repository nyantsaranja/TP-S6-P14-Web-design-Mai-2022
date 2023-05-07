import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {Articles} from "./mothercomponents/Articles";
import {Article} from "./mothercomponents/Article";
import {AddArticle} from "./mothercomponents/AddArticle";
import {ModifyArticle} from "./mothercomponents/ModifyArticle";
import {Login} from "./mothercomponents/Login";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Login/>}></Route>
                <Route path="/ai-blog/articles" element={<Articles/>}></Route>
                <Route path="/ai-blog/article/:params" element={<Article/>}></Route>
                <Route path="/ai-blog/add-article" element={<AddArticle/>}></Route>
                <Route path="/ai-blog/update-article/:id" element={<ModifyArticle/>}></Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
