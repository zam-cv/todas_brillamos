import { createContext } from 'react';
import { NavigateFunction  } from "react-router-dom";


export type AuthContextType = {
    isLoading: boolean;
    isAuthenticated: boolean;   
    signOut: () => void;
    signIn: (email: string, password: string, navigate: NavigateFunction) => void;
}

export const AuthContext = createContext<AuthContextType>({
    isLoading: true,
    isAuthenticated: false,
    signOut: () => {},
    signIn: () => {},
 
});
