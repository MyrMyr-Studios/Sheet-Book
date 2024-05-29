import { BrowserRouter, Routes, Route } from "react-router-dom";
import Blogs from "./pages/blogs";
import Home from "./pages/home";
import Layout from "./pages/layout";
import axios from 'axios'

axios.defaults.baseURL = 'http://localhost:8080'
axios.defaults.withCredentials = true

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route index element={<Home />} />
        <Route path="blogs" element={<Blogs />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
