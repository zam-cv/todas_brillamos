import { useEffect, useState } from "react";
import { AuthContext } from "@/contexts/AuthContext";
import { NavigateFunction  } from "react-router-dom";
import api from '@/utils/api/admin';

export default function AuthProvider({ children }: { children: React.ReactNode}) {
    const [isLoading, setIsLoading] = useState(true);
    const [isAuthenticated, setIsAuthenticated] = useState(false);
      
    useEffect(() => {
        const token = localStorage.getItem("token");

        if(token){
            api.admin
            .verifyAdmin()
            .then(() => {
                setIsAuthenticated(true);
                setIsLoading(false);
            })
            .catch(() => {
                setIsAuthenticated(false);
                setIsLoading(false);
            });
        } else {
            setIsAuthenticated(false)
            setIsLoading(false);
        }
    }, []);

    function signOut() {
        localStorage.removeItem("token");
        setIsAuthenticated(false);
    }

    function signIn(email: string, password: string, navigate: NavigateFunction){
        setIsLoading(true);
        api.admin
            .loginAdmin(email, password)
            .then((data) => {
                localStorage.setItem('token', data.token);
                setIsAuthenticated(true);
                setIsLoading(false);
                console.log('Login success, navigating...');
                navigate("/dashboard")
            })
            .catch((error) => {
                setIsLoading(false);
                setIsAuthenticated(false);
                console.error(error);
            });
    }

    return (
        <AuthContext.Provider value={
            {
                isLoading,
                isAuthenticated,
                signOut,
                signIn,
                    
            }
        }>
          {children}
          </AuthContext.Provider> 
    );
}