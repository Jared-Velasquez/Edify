/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        primary: "rgb(0, 12, 40)",
        dimWhite: "rgba(255, 255, 255, 0.7)",
        richBlack: "#0D1821",
        indigoDye: "#344966",
        powderBlue: "#B4CDED",
        babyPowder: "#F0F4EF"
      },
      fontFamily: {
        raleway: ["Raleway", "sans-serif"],
        roboto: ["Roboto Condensed", "sans-serif"],
        cabin: ["Cabin", "sans-serif"],
      },
    },
    screens: {
      xs: "480px",
      ss: "620px",
      sm: "768px",
      md: "1060px",
      lg: "1200px",
      xl: "1700px",
    }
  },
  plugins: [],
}

