import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "@/hooks/useAuth";

export default function Protected({ children }: { children: React.ReactNode}){
    const navigate = useNavigate();
    const { isAuthenticated } = useAuth();

    useEffect(() => {
        if(!isAuthenticated) navigate("/");
    }, [isAuthenticated, navigate]);

    return <>{children}</>
}