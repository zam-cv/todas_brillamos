import { useEffect, useState } from "react";
import { AuthContext } from "@/contexts/AuthContext";
import { NavigateFunction  } from "react-router-dom";
import api from '@/utils/api/admin';

/**
 * Proveedor de autenticación
 * @author Sebastian Antonio Almanza
 */
export default function AuthProvider({ children }: { children: React.ReactNode}) {
    // Estados para verificar si esta cargando y si esta autenticado
    const [isLoading, setIsLoading] = useState(true);
    const [isAuthenticated, setIsAuthenticated] = useState(false);
      
    //Verificar si hay token válido
    useEffect(() => {
        const token = localStorage.getItem("token");

        if(token){
            // SI hay un token verificarlo 
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
            // Si no hay token no esta autenticado
            setIsAuthenticated(false)
            setIsLoading(false);
        }
    }, []);

    // función para cerrar sesión
    function signOut() {
        localStorage.removeItem("token");
        setIsAuthenticated(false);
    }

    // función para iniciar sesión
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