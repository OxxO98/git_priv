const modal = document.getElementById("modal");
const modalContent = document.getElementById("modalContent");

async function openModal(no) {
    modal.showModal();

    modalContent.innerHTML = "Loading...";

    try {
        const response = await fetch(`result?no=${no}`);

        if (!response.ok) {
            throw new Error("페이지를 불러오지 못했습니다.");
        }

        const html = await response.text();

        modalContent.innerHTML = html;

    } catch (error) {
        modalContent.innerHTML = error.message;
    }
}

function closeModal() {
    modal.close();
    modalContent.innerHTML = "";
}