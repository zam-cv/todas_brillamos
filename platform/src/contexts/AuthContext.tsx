import { createContext } from 'react';
import { NavigateFunction  } from "react-router-dom";

/**
 * Contexto de autenticación
 * @author Sebastian Antonio Almanza
 */
export type AuthContextType = {
    isLoading: boolean;
    isAuthenticated: boolean;   
    signOut: () => void;
    signIn: (email: string, password: string, navigate: NavigateFunction) => void;
}
//Crea el contexto de autenticación con los valores predeterminados
export const AuthContext = createContext<AuthContextType>({
    isLoading: true, // Por defecto, se asume que esta en proceso la autenticación
    isAuthenticated: false, // Por defecto, no esta autenticado
    signOut: () => {}, // función vacia para cerrar sesión
    signIn: () => {}, // función vacía para iniciar sesión
 
});
