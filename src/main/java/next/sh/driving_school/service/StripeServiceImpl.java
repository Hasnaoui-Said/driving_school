package next.sh.driving_school.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeServiceImpl implements StripeService{

    @Value("${stripe.api.key}")
    private String apiKey;

    @Override
    public Charge chargeCard(String token, long amount) throws StripeException {
        Stripe.apiKey = this.apiKey;

        Charge charge = Charge.create(
                new ChargeCreateParams.Builder()
                        .setAmount(amount)
                        .setCurrency("usd")
                        .setDescription("Charge for " + token)
                        .setSource(token).build()
        );
        return charge;
    }
}

