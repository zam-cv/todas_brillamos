import { ColumnDef } from "@tanstack/react-table";
import { Task } from "@/components/table/components/tableDonations/schema_donations";
import { DataTableColumnHeader } from "./data-table-column-header-donations";

export const columns: ColumnDef<Task>[] = [
    {
        accessorKey: "product_name",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} title="Nombre de producto" />
        ),
        cell: ({ row }) => {
          return (
            <div className="flex space-x-2">
              <span className="max-w-[500px] truncate font-medium">
                {row.getValue("product_name")}
              </span>
            </div>
          );
        },
      },
    {
      accessorKey: "amount",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Cantidad" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("amount")}
            </span>
          </div>
        );
      },
    },  
    {
        accessorKey: "user_email",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} title="Email del donante" />
        ),
        cell: ({ row }) => {
          return (
            <div className="flex space-x-2">
              <span className="max-w-[500px] truncate font-medium">
                {row.getValue("user_email")}
              </span>
            </div>
          );
        },
      }, 
      {
        accessorKey: "first_name",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} title="Primer nombre" />
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
          <DataTableColumnHeader column={column} title="Segundo nombre" />
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
  ];
  