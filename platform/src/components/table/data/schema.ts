import {z} from "zod"

export const taskSchema = z.object({
    id: z.string(),
    username: z.string(),
    firstname: z.string(),
    lastname: z.string(),
    team: z.string(),
    personal_email: z.string(),
    phone: z.string(),
    semester: z.string(),
    campus: z.string(),
    major: z.string(),
    email: z.string(),
    confirmed: z.boolean(),
    with_bus: z.boolean(),
    event_id: z.string(),
  })
  
  export type Task = z.infer<typeof taskSchema>