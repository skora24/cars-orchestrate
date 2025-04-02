import type { NextConfig } from "next";

const nextConfig: NextConfig = {
    /* config options here */
    images: {
        remotePatterns: [
            {
                protocol: "https",
                hostname: "cs.copart.com",
                port: "",
                pathname: "/**",
                search: "",
            },
        ],
    },
};

export default nextConfig;
