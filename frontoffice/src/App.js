import logo from './logo.svg';
import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {Articles} from "./components/mother/Articles";
import {Article} from "./components/mother/Article";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Articles/>}></Route>
                <Route path="/blog-ai/articles" element={<Articles/>}></Route>
                <Route path="/blog-ai/article/:params" element={<Article/>}></Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
