import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { columns } from "@/components/table/components/columns";
import { DataTable } from "@/components/table/components/data-table";

export default function Users(){

  const mockParticipants = [
    { idUsuario: '1', nombre: 'John', apellido: 'Doe', personal_email: 'john.doe@example.com', phone: '123-456-7890', curp: '2132818181', street: 'Calle #1', ZIP: '1213213'}
    // Add more mock participants as needed
  ];

    return (
        <div>
             <h2 className="scroll-m-20 border-b pb-2 text-3xl font-semibold tracking-tight first:mt-0">
          Usuarios
      </h2>   
      <div className="h-full flex-1 flex-col space-y-8 p-8 flex">
        <div className="flex items-center justify-between space-y-2">
          <div>
            <h2 className="text-2xl font-bold tracking-tight">Users</h2>
            <p className="text-muted-foreground">
              Here&apos;s a list of users for this event.
            </p>
          </div>
        </div>
        <DataTable
          data = {mockParticipants}
          columns={columns}
          event_id={1}
        />
      </div>










        </div>
    )
}