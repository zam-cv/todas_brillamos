import {z} from "zod"

export const taskSchema = z.object({
    idUsuario: z.string(),
    nombre: z.string(),
    apellido: z.string(),
    personal_email: z.string(),
    phone: z.string(),
    curp: z.string().optional(),
    street: z.string().optional(),
    ZIP: z.string().optional(),
  })
  
  export type Task = z.infer<typeof taskSchema>