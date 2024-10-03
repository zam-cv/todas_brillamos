import "./App.css";

import { BrowserRouter, Routes, Route } from "react-router-dom";

// Layouts
import Layout from "@/layouts/Layout";
import BasicLayout from "@/layouts/BasicLayout";

//Security
import Unprotected from "@/components/Unprotected";
import Protected from "@/components/Protected";

// Pages
import Home from "@/pages/index";
import Terms from "./pages/Terms";
import Privacy from "./pages/Privacy";

import Dashboard from "./pages/Dashboard";
import Products from "./pages/Products";
import Shipments from "./pages/Shipments";
import Specialists from "./pages/Specialists";
import Users from "./pages/Users";
import Notifications from "./pages/Notifications";
import AuthProvider from "./providers/AuthProvider";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<BasicLayout />}>
            <Route index element={
              <Unprotected>
                <Home />
              </Unprotected>
              } />
          </Route>

          <Route path="/login" element={<Layout />}>
            <Route index element={
              <Protected>
                <Dashboard />
              </Protected>
              } />
          </Route>

          <Route path="/terms" element={<Layout />}>
            <Route index element={<Terms />} />
          </Route>

          <Route path="/privacy" element={<Layout />}>
            <Route index element={<Privacy />} />
          </Route>

          <Route path="/dashboard" element={<Layout />}>
            <Route index element={
              <Protected>
                <Dashboard />
              </Protected>
              } />
          </Route>

          <Route path="/products" element={<Layout />}>
            <Route index element={
              <Protected>
                <Products />
              </Protected>
              } />
          </Route>

          <Route path="/shipments" element={<Layout />}>
            <Route index element={
              <Protected>
                <Shipments />
              </Protected>
              } />
          </Route>

          <Route path="/specialists" element={<Layout />}>
            <Route index element={
              <Protected>
              <Specialists />
              </Protected>
              
              } />
          </Route>

          <Route path="/users" element={<Layout />}>
            <Route index element={
              <Protected>
              <Users />
              </Protected>
              } />
          </Route>

          <Route path="/notifications" element={<Layout />}>
            <Route index element={
              <Protected>
              <Notifications />
              </Protected>
              } />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
