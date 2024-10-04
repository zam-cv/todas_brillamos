import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "@/hooks/useAuth";

export default function Unprotected({
    children,
}: {
    children: React.ReactNode;
}) {
    const navigate = useNavigate();
    const { isAuthenticated } = useAuth();

    useEffect(() => {
        if(isAuthenticated) navigate("/dashboard");
    }, [isAuthenticated, navigate]);

    return <>{children}</>
}