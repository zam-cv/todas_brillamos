import {z} from "zod"

export const taskSchema = z.object({
    client_id: z.number(),
    first_name: z.string(),
    last_name: z.string(),
    email: z.string(),
    curp: z.string(),
    street: z.string(),
    z_ip: z.string(),
    reference: z.string()
  })
  
  export type Task = z.infer<typeof taskSchema>