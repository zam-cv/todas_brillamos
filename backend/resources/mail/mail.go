package mail

import (
	"context"
	"fmt"
	"time"

	"github.com/mailersend/mailersend-go"
)

type EmailPayload struct {
	Title  string
	Body   string
	Emails []string
}

type MailerSendConfig struct {
	APIKey    string
	FromName  string
	FromEmail string
}

// SendEmail envía un correo electrónico a los destinatarios especificados.
func SendEmail(payload EmailPayload, api, email string) error {
	config := MailerSendConfig{
		APIKey:    api,
		FromName:  "Todas Brillamos",
		FromEmail: email,
	}

	ms := mailersend.NewMailersend(config.APIKey)

	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	from := mailersend.From{
		Name:  config.FromName,
		Email: config.FromEmail,
	}

	recipients := make([]mailersend.Recipient, len(payload.Emails))
	for i, email := range payload.Emails {
		recipients[i] = mailersend.Recipient{
			Email: email,
		}
	}

	message := ms.NewMessage()

	message.SetFrom(from)
	message.SetRecipients(recipients)
	message.SetSubject(payload.Title)
	message.SetHTML(payload.Body)
	message.SetText(payload.Body)

	res, err := ms.Send(ctx, message)
	if err != nil {
		return fmt.Errorf("failed to send email: %v", err)
	}

	fmt.Printf("Email sent successfully. Message ID: %s\n", res.Header.Get("X-Message-Id"))
	return nil
}

// Register envía un correo electrónico de registro a la dirección de correo electrónico especificada.
func Register(email string, apiKey, sender string) {
	payload := EmailPayload{
		Title:  "Registro en Todas Brillamos",
		Body:   "¡Gracias por registrarte!",
		Emails: []string{email},
	}

	SendEmail(payload, apiKey, sender)
}

// NewPost envía un correo electrónico a los destinatarios especificados cuando se crea un nuevo post.
func NewPost(emails []string, postTitle string, apiKey, sender string) {
	payload := EmailPayload{
		Title:  "Nuevo post",
		Body:   "Se ha creado un nuevo post, ¡no te lo pierdas!: " + postTitle,
		Emails: emails,
	}

	SendEmail(payload, apiKey, sender)
}

// BuyConfirmation envía un correo electrónico de compra a la dirección de correo electrónico especificada.
func BuyConfirmation(email string, apiKey, sender string) {
	payload := EmailPayload{
		Title:  "Compra confirmada",
		Body:   "Tu compra ha sido confirmada. ¡Gracias por tu preferencia!",
		Emails: []string{email},
	}

	SendEmail(payload, apiKey, sender)
}

func UpdateOrderStatus(status, email, apiKey, sender string) {
	payload := EmailPayload{
		Title:  "Estado de orden actualizado",
		Body:   "El estado de tu orden ha sido actualizado a: " + status,
		Emails: []string{email},
	}

	SendEmail(payload, apiKey, sender)
}
