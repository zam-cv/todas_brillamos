import { useEffect, useState } from "react";
import { columns } from "@/components/table/components/columns-users";
import { DataTable } from "@/components/table/components/data-table-users";
import api, { ClientInfo } from "@/utils/api/client";

/**
 * Página de usuarios
 * @author Sebastian Antonio Almanza 
 */
export default function Users(){
  // constante para almacenar los datos de los usuarios
  const [ids, setIds] = useState<number[]> ([]);
  const [clientInfo, setClientInfo] = useState<ClientInfo[]>([]);

  //Obtener los ids de los usuarios
  useEffect(() => {
    api.client.getClientsIDs().then((ids) => {
      setIds(ids);
    });
  }, []);

  //Obtener la información de los usuarios
  function getClientInfo(ids: number[]) {
    const clientPromises = ids.map((id) => api.client.getClientsInfo(id));

    Promise.all(clientPromises)
      .then((clients) => {
        console.log(clients);
        setClientInfo(clients.flat());
      })
      .catch((error) => {
        console.error("Error fetching client info:", error);
      });
  }

  //Obtener la información de los usuarios
  useEffect(() => {
    if (ids.length > 0) {
      getClientInfo(ids);
    }
  }, [ids]);

    return (
      <div>
          <h2 className="scroll-m-20  text-3xl font-semibold tracking-tight first:mt-0">
              Usuarios
          </h2>
          <br></br>  
           
          <div>
              <DataTable
                data = {clientInfo}
                columns={columns}
                event_id={1}
              />
          </div>
      </div>
    )
}