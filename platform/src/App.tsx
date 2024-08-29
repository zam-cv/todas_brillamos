import "./App.css";

import { BrowserRouter, Routes, Route } from "react-router-dom";

// Layouts
import Layout from "@/layouts/Layout";
import BasicLayout from "@/layouts/BasicLayout";

// Pages
import Home from "@/pages/index";
import Terms from "./pages/Terms";
import Privacy from "./pages/Privacy";

import Dashboard from "./pages/Dashboard";
import Products from "./pages/Products";
import Shipments from "./pages/Shipments";
import Specialists from "./pages/Specialists";
import Users from "./pages/Users";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<BasicLayout />}>
          <Route index element={<Home />} />
        </Route>

        <Route path="/login" element={<Layout />}>
          <Route index element={<Dashboard />} />
        </Route>

        <Route path="/terms" element={<Layout />}>
          <Route index element={<Terms />} />
        </Route>

        <Route path="/privacy" element={<Layout />}>
          <Route index element={<Privacy />} />
        </Route>

        <Route path="/dashboard" element={<Layout />}>
          <Route index element={<Dashboard />} />
        </Route>
        
        <Route path="/products" element={<Layout />}>
          <Route index element={<Products />} />
        </Route>

        <Route path="/shipments" element={<Layout />}>
          <Route index element={<Shipments />} />
        </Route>

        <Route path="/specialists" element={<Layout />}>
          <Route index element={<Specialists />} />
        </Route>

        <Route path="/users" element={<Layout />}>
          <Route index element={<Users />} />
        </Route>
        
      </Routes>
    </BrowserRouter>
  );
}

export default App;
