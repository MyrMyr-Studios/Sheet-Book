import { BrowserRouter, Routes, Route } from "react-router-dom";
import axios from 'axios'
import Home from "./pages/home";
import Register from "./pages/register";
import Profile from "./pages/profile";
import Campaigns from "./pages/campaigns";
import Sheets from "./pages/sheets";
import SheetEdit from "./pages/sheet_edit";

axios.defaults.baseURL = 'http://localhost:8080'
axios.defaults.withCredentials = true

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route index element={<Home />} />
        <Route path="register" element={<Register />} />
        <Route path="profile" element={<Profile />} />
        <Route path="campaigns" element={<Campaigns />} />
        <Route path="sheets" element={<Sheets />} />
        <Route path="sheets/edit" element={<SheetEdit />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
