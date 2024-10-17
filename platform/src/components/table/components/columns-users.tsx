import { ColumnDef } from "@tanstack/react-table";
import { Task } from "../data/schema";
import { DataTableColumnHeader } from "./data-table-column-header-users";

export const columns: ColumnDef<Task>[] = [
    {
        accessorKey: "client_id",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} title="ID" />
        ),
        cell: ({ row }) => {
          return (
            <div className="flex space-x-2">
              <span className="max-w-[500px] truncate font-medium">
                {row.getValue("client_id")}
              </span>
            </div>
          );
        },
      },
    {
      accessorKey: "first_name",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Nombre" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("first_name")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "last_name",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Apellido" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("last_name")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "email",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Email" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("email")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "curp",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="CURP" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("curp")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "street",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Calle" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("street")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "z_ip",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Código Postal" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("z_ip")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "reference",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Referencia" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("reference")}
            </span>
          </div>
        );
      },
    },
    
  ];
  