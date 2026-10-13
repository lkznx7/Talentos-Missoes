import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "Talentos & Missões",
  description: "Estrutura inicial do frontend.",
};

export default function RootLayout({ children }: Readonly<{ children: React.ReactNode }>) {
  return <html lang="pt-BR"><body>{children}</body></html>;
}
