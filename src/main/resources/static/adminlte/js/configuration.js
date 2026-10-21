const input = document.getElementById("logoInput");
const preview = document.getElementById("logoPreview");

input.addEventListener("change", function () {

    const file = this.files[0];

    if (!file) return;

    // Validar tamaño (2 MB)
    if (file.size > 2 * 1024 * 1024) {
        alert("La imagen no debe superar los 2 MB.");
        this.value = "";
        return;
    }

    // Validar tipo
    const allowed = [
        "image/png",
        "image/jpeg",
        "image/jpg",
        "image/webp"
    ];

    if (!allowed.includes(file.type)) {
        alert("Formato no permitido.");
        this.value = "";
        return;
    }

    // Vista previa
    const reader = new FileReader();

    reader.onload = function (e) {
        preview.src = e.target.result;
    };

    reader.readAsDataURL(file);

});