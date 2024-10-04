import { useContext } from "react";
import { AuthContext, AuthContextType } from "@/contexts/AuthContext";

export function useAuth(): AuthContextType{
    return useContext(AuthContext);
}