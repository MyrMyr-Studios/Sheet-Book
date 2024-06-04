import { BrowserRouter, Routes, Route } from "react-router-dom";
import Register from "./pages/register";
import Home from "./pages/home";
import axios from 'axios'

axios.defaults.baseURL = 'http://localhost:8080'
axios.defaults.withCredentials = true

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route index element={<Home />} />
        <Route path="register" element={<Register />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
