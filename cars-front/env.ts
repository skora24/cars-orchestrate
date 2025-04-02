import { createEnv } from "@t3-oss/env-nextjs";
import { z } from "zod";

export const env = createEnv({
    server: {
        GATEWAY_URL: z.string().url(),
    },
    client: {},
    experimental__runtimeEnv: {},
});
