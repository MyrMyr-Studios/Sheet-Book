import { plugin } from 'postcss'

/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  theme: {
    extend: {},
  },
  plugins: [require("daisyui")],
}

module.exports = {
  content: ["node_modules/daisyui/dist/**/*.js", "node_modules/react-daisyui/dist/**/*.js"],
  plugins: [require("daisyui")],
}
