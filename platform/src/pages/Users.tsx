import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { columns } from "@/components/table/components/columns-users";
import { DataTable } from "@/components/table/components/data-table-users";

export default function Users(){

  const mockParticipants = [
    { idUsuario: '1', 
      nombre: 'John', 
      apellido: 'Doe', 
      personal_email: 'john.doe@example.com', 
      phone: '123-456-7890', 
      curp: '2132818181', 
      street: 'Calle #1', 
      ZIP: '1213213'},
      { idUsuario: '1', 
        nombre: 'John', 
        apellido: 'Doe', 
        personal_email: 'john.doe@example.com', 
        phone: '123-456-7890', 
        curp: '2132818181', 
        street: 'Calle #1', 
        ZIP: '1213213'},
        { idUsuario: '1', 
          nombre: 'John', 
          apellido: 'Doe', 
          personal_email: 'john.doe@example.com', 
          phone: '123-456-7890', 
          curp: '2132818181', 
          street: 'Calle #1', 
          ZIP: '1213213'},
          { idUsuario: '1', 
            nombre: 'John', 
            apellido: 'Doe', 
            personal_email: 'john.doe@example.com', 
            phone: '123-456-7890', 
            curp: '2132818181', 
            street: 'Calle #1', 
            ZIP: '1213213'},
    // Add more mock participants as needed
  ];

    return (
      <div>
          <h2 className="scroll-m-20  text-3xl font-semibold tracking-tight first:mt-0">
              Usuarios
          </h2>
          <br></br>  
            <div className="flex items-center justify-between space-y-2">
            </div>
            <div>
              <DataTable
                data = {mockParticipants}
                columns={columns}
                event_id={1}
              />
          </div>
      </div>
    )
}