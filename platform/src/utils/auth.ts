interface Config {
    withCredentials: boolean;
    headers: {
        Authorization: string | null;
    };
}

export function getConfig(): Config {
    const token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MjkxODE2MjAsImlkIjoiMSJ9.MSNPrnj2VY6poFNlyS40Bo4n8mKMFjWe9ACyx4Ngkj0"; // Obtén el token del almacenamiento
    return {
        withCredentials: true,
        headers: {
            Authorization: token ? `Bearer ${token}` : null, // Asegura que haya un espacio después de 'Bearer'
        }
    };
}
