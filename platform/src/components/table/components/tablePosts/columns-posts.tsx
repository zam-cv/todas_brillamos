import { useState } from "react";
import { ColumnDef } from "@tanstack/react-table";
import { Checkbox } from "@/components/ui/checkbox";
import { Task } from "@/components/table/components/tablePosts/schema_posts";
import { DataTableColumnHeader } from "./data-table-column-header-posts";
import {
  AlertDialog,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog"
import { Button } from "@/components/ui/button";
import api from "@/utils/api/post"


function handleDelete(id: number) {
  api.posts.deletePost(id)
}


export const columns: ColumnDef<Task>[] = [
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
      accessorKey: "elimnar",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Eliminar" />
      ),
      cell: ({ row }) => {
        return (
          <AlertDialog>
            <AlertDialogTrigger asChild>
              <Button variant="destructive"
              >Eliminar</Button>
            </AlertDialogTrigger>
            <AlertDialogContent>
              <AlertDialogHeader>
                <AlertDialogTitle>Eliminar Artículo</AlertDialogTitle>
                <AlertDialogDescription>
                  <p>Deseas eliminar este artículo?</p>
                </AlertDialogDescription>
              </AlertDialogHeader>
              <AlertDialogFooter>
                <AlertDialogCancel onClick={() => handleDelete((row.original as any).id)}>Cerrar</AlertDialogCancel>
              </AlertDialogFooter>
            </AlertDialogContent>
          </AlertDialog>
        );
      },
    },
    
    
  ];
  