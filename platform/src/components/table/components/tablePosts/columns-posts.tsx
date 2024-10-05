import { ColumnDef } from "@tanstack/react-table";
import { Task } from "@/components/table/components/tablePosts/schema_posts";
import { DataTableColumnHeader } from "./data-table-column-header-posts";
import { Button } from "@/components/ui/button";

export const createColumns = (handleDelete: (postId: number) => void): ColumnDef<Task>[] => [
  {
    accessorKey: "title",
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title="Título" />
    ),
    cell: ({ row }) => {
      return (
        <div className="flex space-x-2">
          <span className="max-w-[500px] truncate font-medium">
            {row.getValue("title")}
          </span>
        </div>
      );
    },
  },
  {
    accessorKey: "author",
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title="Autor" />
    ),
    cell: ({ row }) => {
      return (
        <div className="flex space-x-2">
          <span className="max-w-[500px] truncate font-medium">
            {row.getValue("author")}
          </span>
        </div>
      );
    },
  },
  {
    accessorKey: "date",
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title="Fecha" />
    ),
    cell: ({ row }) => {
      return (
        <div className="flex space-x-2">
          <span className="max-w-[500px] truncate font-medium">
            {row.getValue("date")}
          </span>
        </div>
      );
    },
  },
  {
    id: "actions",
    header: "Acciones",
    cell: ({ row }) => {
      return (
        <div className="flex justify-end">
          <Button 
            variant="destructive" 
            onClick={() => handleDelete((row.original as any).id)}
          >
            Eliminar
          </Button>
        </div>
      );
    },
  },
];